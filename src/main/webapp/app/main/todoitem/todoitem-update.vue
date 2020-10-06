<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="todolistApp.todoitem.home.createOrEditLabel">Create or edit a Todoitem</h2>
                <div>
                    <div class="form-group" v-if="todoitem.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="todoitem.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="todoitem-created">Created</label>
                        <div class="d-flex">
                            <input id="todoitem-created" type="datetime-local" class="form-control" name="created" :class="{'valid': !$v.todoitem.created.$invalid, 'invalid': $v.todoitem.created.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.todoitem.created.$model)"
                            @change="updateZonedDateTimeField('created', $event)"/>
                        </div>
                        <div v-if="$v.todoitem.created.$anyDirty && $v.todoitem.created.$invalid">
                            <small class="form-text text-danger" v-if="!$v.todoitem.created.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.todoitem.created.ZonedDateTimelocal">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="todoitem-status">Status</label>
                        <input type="text" class="form-control" name="status" id="todoitem-status"
                            :class="{'valid': !$v.todoitem.status.$invalid, 'invalid': $v.todoitem.status.$invalid }" v-model="$v.todoitem.status.$model"  required/>
                        <div v-if="$v.todoitem.status.$anyDirty && $v.todoitem.status.$invalid">
                            <small class="form-text text-danger" v-if="!$v.todoitem.status.required">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="todoitem-task">Task</label>
                        <textarea class="form-control" name="task" id="todoitem-task"
                            :class="{'valid': !$v.todoitem.task.$invalid, 'invalid': $v.todoitem.task.$invalid }" v-model="$v.todoitem.task.$model"  required></textarea>
                        <div v-if="$v.todoitem.task.$anyDirty && $v.todoitem.task.$invalid">
                            <small class="form-text text-danger" v-if="!$v.todoitem.task.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.todoitem.task.maxLength">
                                This field cannot be longer than 1024 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="todoitem-user">User</label>
                        <select class="form-control" id="todoitem-user" name="user" v-model="todoitem.user" required>
                            <option v-if="!todoitem.user" v-bind:value="null" selected></option>
                            <option v-bind:value="todoitem.user && userOption.id === todoitem.user.id ? todoitem.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div v-if="$v.todoitem.user.$anyDirty && $v.todoitem.user.$invalid">
                        <small class="form-text text-danger" v-if="!$v.todoitem.user.required">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.todoitem.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./todoitem-update.component.ts">
</script>
