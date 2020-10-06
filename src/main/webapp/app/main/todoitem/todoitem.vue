<template>
    <div>
        <h2 id="page-heading">
            <span id="todoitem-heading">Todoitems</span>
            <router-link :to="{name: 'TodoitemCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-todoitem">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Todoitem
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && todoitems && todoitems.length === 0">
            <span>No todoitems found</span>
        </div>
        <div class="form-group">
            <label class="form-control-label" for="status">Status</label>
            <select @change.prevent="changeStatus" class="form-control" id="status" name="status" v-model="filter" required>
                <option :value="0">All</option>
                <option :value="item" v-for="item in 2" :key="item">{{ (item === 1)?'created':'completed'}}</option>
            </select>
        </div>
        <div class="table-responsive" v-if="todoitems && todoitems.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('created')"><span>Created</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'created'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('status')"><span>Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('task')"><span>Task</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'task'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="todoitem in todoitems"
                    :key="todoitem.id">
                    <td>
                        <router-link :to="{name: 'TodoitemView', params: {todoitemId: todoitem.id}}">{{todoitem.id}}</router-link>
                    </td>
                    <td>{{todoitem.created | formatDate}}</td>
                    <td>{{todoitem.status}}</td>
                    <td>{{todoitem.task}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TodoitemView', params: {todoitemId: todoitem.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'TodoitemEdit', params: {todoitemId: todoitem.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(todoitem)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="todolistApp.todoitem.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-todoitem-heading">Are you sure you want to delete this Todoitem?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-todoitem" v-on:click="removeTodoitem()">Delete</button>
            </div>
        </b-modal>
        <div v-show="todoitems && todoitems.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./todoitem.component.ts">
</script>
