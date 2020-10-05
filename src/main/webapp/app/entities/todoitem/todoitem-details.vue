<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="todoitem">
                <h2 class="jh-entity-heading"><span>Todoitem</span> {{todoitem.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Created</span>
                    </dt>
                    <dd>
                        <span>{{todoitem.created | formatDate}}</span>
                    </dd>
                    <dt>
                        <span>Status</span>
                    </dt>
                    <dd>
                        <span>{{todoitem.status}}</span>
                    </dd>
                    <dt>
                        <span>Task</span>
                    </dt>
                    <dd>
                        <span>{{todoitem.task}}</span>
                    </dd>
                    <dt>
                        <span>User</span>
                    </dt>
                    <dd>
                        {{todoitem.user ? todoitem.user.login : ''}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="todoitem.id" :to="{name: 'TodoitemEdit', params: {todoitemId: todoitem.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./todoitem-details.component.ts">
</script>
